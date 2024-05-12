CREATE TABLE IF NOT EXISTS tasklist (
    id   VARCHAR(8)  PRIMARY KEY,
    task VARCHAR(30),
    category VARCHAR(10),
    manager VARCHAR(10),
    start VARCHAR(10),
    deadline VARCHAR(10),
    description VARCHAR(1000),
    status VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS categorylist(
    category  VARCHAR(10)
);
INSERT INTO categorylist (category) SELECT ('事務') WHERE NOT EXISTS (SELECT category FROM categorylist WHERE category =('事務'));
INSERT INTO categorylist (category) SELECT ('開発') WHERE NOT EXISTS (SELECT category FROM categorylist WHERE category =('開発'));


CREATE TABLE IF NOT EXISTS managerlist(
    manager  VARCHAR(10)
);

INSERT INTO managerlist (manager) SELECT ('藤野裕実') WHERE NOT EXISTS (SELECT manager FROM managerlist WHERE manager =('藤野裕実'));
INSERT INTO managerlist (manager) SELECT ('小森早葉子') WHERE NOT EXISTS (SELECT manager FROM managerlist WHERE manager =('小森早葉子'));
INSERT INTO managerlist (manager) SELECT ('足立朗夫') WHERE NOT EXISTS (SELECT manager FROM managerlist WHERE manager =('足立朗夫'));
INSERT INTO managerlist (manager) SELECT ('岸本美砂') WHERE NOT EXISTS (SELECT manager FROM managerlist WHERE manager =('岸本美砂'));
INSERT INTO managerlist (manager) SELECT ('川端督彦') WHERE NOT EXISTS (SELECT manager FROM managerlist WHERE manager =('川端督彦'));


CREATE TABLE IF NOT EXISTS statuslist(
    status  VARCHAR(10)
);

INSERT INTO statuslist (status) SELECT ('未完了') WHERE NOT EXISTS (SELECT status FROM statuslist WHERE status =('未完了'));
INSERT INTO statuslist (status) SELECT ('対応中') WHERE NOT EXISTS (SELECT status FROM statuslist WHERE status =('対応中'));
INSERT INTO statuslist (status) SELECT ('完了') WHERE NOT EXISTS (SELECT status FROM statuslist WHERE status =('完了'));

CREATE TABLE IF NOT EXISTS account (
  id        INTEGER         NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(128)    NOT NULL,
  password   VARCHAR(512)    NOT NULL,
  user_name_kanji VARCHAR(512)    NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO account (user_name, password,user_name_kanji) SELECT 'user1', '$2a$10$T7dckfpU4EC0nKxEKKYukeTa1ZXGBtKc5ZrEFfVNZ/CGoYmUDRE3e', 'ユーザー' WHERE NOT EXISTS (SELECT 1 FROM account WHERE user_name =('user1'));
INSERT INTO account (user_name, password,user_name_kanji) SELECT 'fujino', '$2a$08$Nlgcq6/kdL9p/3.LwiZuGu9JYepJeUgrRYoWFkrlsyJS/i3nQNCE.', '藤野裕実' WHERE NOT EXISTS (SELECT 2 FROM account WHERE user_name =('fujino'));
INSERT INTO account (user_name, password,user_name_kanji) SELECT 'komori', '$2a$08$fUNxIyDCJ2wCFWYPFWRXFOvPD4oEjnjSz/148rTldLBE/.9rHMGvC' ,'小森早葉子' WHERE NOT EXISTS (SELECT 3 FROM account WHERE user_name =('komori'));
INSERT INTO account (user_name, password,user_name_kanji) SELECT 'adachi', '$2a$08$B6LkxsRDwJIKnrucLhuWJ.PLZPzSnxqN.VPbwrzjgfbil8e9v2TGG' ,'足立朗夫' WHERE NOT EXISTS (SELECT 4 FROM account WHERE user_name =('adachi'));
INSERT INTO account (user_name, password,user_name_kanji) SELECT 'kishimoto', '$2a$08$Ccb8yQdeqAMjzm4gcHAsR.k1z0bdL8dXRBmH3X9jAkD20NppnVzW6','岸本美砂'  WHERE NOT EXISTS (SELECT 5 FROM account WHERE user_name =('kishimoto'));
INSERT INTO account (user_name, password,user_name_kanji) SELECT 'kawabata', '$2a$08$BLu8D8SP5q3FsQ9ybHK3XORHr0ZL5QFSt/KKYtI3GUq7wxbzSaNXW' ,'川端督彦' WHERE NOT EXISTS (SELECT 6 FROM account WHERE user_name =('kawabata'));
