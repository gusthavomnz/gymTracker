ALTER TABLE exercise_sets DROP FOREIGN KEY fk_set_session;
ALTER TABLE exercise_sets ADD CONSTRAINT fk_set_session
    FOREIGN KEY (session_id) REFERENCES training_sessions (session_id) ON DELETE CASCADE;
