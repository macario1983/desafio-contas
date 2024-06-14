INSERT INTO users (id, username, password)
VALUES
  (gen_random_uuid(), 'user1', '$2a$10$0Gv55wI9TNXxPIWF4lYQKOlmxZWFCfXHuonBKM/xk8GssJVJ/i30m'),
  (gen_random_uuid(), 'user2', '$2a$10$0Gv55wI9TNXxPIWF4lYQKOlmxZWFCfXHuonBKM/xk8GssJVJ/i30m');
