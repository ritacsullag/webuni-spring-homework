ALTER TABLE login_user
    ADD COLUMN facebook_id varchar(255);

ALTER TABLE login_user_aud
    ADD COLUMN facebook_id varchar(255);