INSERT INTO users (id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES
  (gen_random_uuid(), 'user1', 'password123', true, true, true, true),
  (gen_random_uuid(), 'user2', 'password456', true, true, true, true);
