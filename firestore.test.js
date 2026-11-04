const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_123";
const BOB_UID = "bob_456";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

// 1. Users collection
test("Unauthenticated user: cannot read user profile", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).get());
});

test("Authenticated user: can create own profile with valid schema", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).set({
      userId: ALICE_UID,
      displayName: "Alice Numismatist",
      email: "alice@numisma.com",
      tier: "COLLECTOR_ELITE",
      createdAt: new Date(),
      updatedAt: new Date(),
    })
  );
});

test("Authenticated user: cannot read another user's profile", async () => {
  await testEnv.withSecurityRulesDisabled(async (context) => {
    await context.firestore().collection("users").doc(BOB_UID).set({
      userId: BOB_UID,
      displayName: "Bob Dealer",
      email: "bob@numisma.com",
      tier: "PROFESSIONAL",
      createdAt: new Date(),
      updatedAt: new Date(),
    });
  });

  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertFails(aliceDb.collection("users").doc(BOB_UID).get());
});

// 2. Specimens collection (Vault)
test("Unauthenticated user: cannot access specimens", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("specimens").get());
});

test("Authenticated user: can create a specimen in vault", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertSucceeds(
    aliceDb.collection("specimens").doc("specimen_1").set({
      userId: ALICE_UID,
      coinName: "Athenian Owl Tetradrachm",
      grade: "Ch XF",
      gradingService: "NGC",
      metal: "Silver",
      era: "Classical Greece",
      measuredWeightGrams: 17.18,
      createdAt: new Date(),
      updatedAt: new Date(),
    })
  );
});

test("Authenticated user: cannot read another user's specimen", async () => {
  await testEnv.withSecurityRulesDisabled(async (context) => {
    await context.firestore().collection("specimens").doc("bob_coin").set({
      userId: BOB_UID,
      coinName: "Roman Aureus Julius Caesar",
      grade: "MS63",
      gradingService: "NGC",
      createdAt: new Date(),
      updatedAt: new Date(),
    });
  });

  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertFails(aliceDb.collection("specimens").doc("bob_coin").get());
});

// 3. Listings collection
test("Authenticated user: can read listings and create listing", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertSucceeds(
    aliceDb.collection("listings").doc("listing_1").set({
      userId: ALICE_UID,
      sellerName: "Alice Numismatics",
      coinTitle: "Gupta Empire Gold Dinar Samudragupta",
      country: "India",
      metal: "Gold",
      grade: "Choice VF",
      price: 1850.0,
      currency: "USD",
      listingType: "FIXED_PRICE",
      status: "ACTIVE",
      description: "Standard archer type gold dinar with high relief.",
      createdAt: new Date(),
      updatedAt: new Date(),
    })
  );

  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  await assertSucceeds(bobDb.collection("listings").doc("listing_1").get());
});
