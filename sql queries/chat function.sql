DROP PROCEDURE chatted;
delimiter $$
CREATE PROCEDURE chatted (
IN chatter varchar(25),
guild varchar(25))
BEGIN
IF ((SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = guild) = 0) THEN
SET @creation = CONCAT('CREATE TABLE `',guild, '` (
  `ID` varchar(25) NOT NULL,
  `money` int(11) NOT NULL,
  `level` tinyint(4) NOT NULL,
  `salary` tinyint(4) NOT NULL,
  `cooldown` tinyint(4) NOT NULL,
  `lastmsg` TIME,
  `daily` datetime,
  `total_msgs` mediumint(8) unsigned DEFAULT 0,
  PRIMARY KEY(`ID`))');
  PREPARE test FROM @creation;
  EXECUTE test;
  DEALLOCATE PREPARE test; 
END IF;

SET @return_value = 2;
SET @creation = CONCAT('SELECT count(ID) INTO @return_value FROM `',guild,'` WHERE ID = \'',chatter,'\'');
PREPARE test FROM @creation;
EXECUTE test;
DEALLOCATE PREPARE test; 

IF ( @return_value = 0) THEN
SET @creation = CONCAT('
INSERT INTO `',guild,'`(`ID`, `money`, `level`, `cooldown`, `salary`) 
VALUES (\'',chatter,'\',20,1,10,1);');
PREPARE test FROM @creation;
EXECUTE test;
DEALLOCATE PREPARE test; 
END IF;


SET @creation = CONCAT('
UPDATE `',guild,'`
SET total_msgs=total_msgs+1
WHERE ID=\'',chatter,'\';');
PREPARE test FROM @creation;
EXECUTE test;
DEALLOCATE PREPARE test; 


SET @creation = CONCAT('
SET @cooldown = (SELECT cooldown FROM `',guild,'` WHERE ID=\'',chatter,'\');');
PREPARE test FROM @creation;
EXECUTE test;
DEALLOCATE PREPARE test; 

SET @creation = CONCAT('
SET @cooldown_time_threshold = (ADDTIME((SELECT lastmsg FROM `',guild,'` WHERE ID=\'',chatter,'\'), @cooldown));');
PREPARE test FROM @creation;
EXECUTE test;
DEALLOCATE PREPARE test; 

SET @creation = CONCAT('
SET @nextday_time_threshold = (SUBTIME((SELECT lastmsg FROM `',guild,'` WHERE ID=\'',chatter,'\'), 10));');
PREPARE test FROM @creation;
EXECUTE test;
DEALLOCATE PREPARE test; 

IF (CURRENT_TIME() > @cooldown_time_threshold OR CURRENT_TIME() < @nextday_time_threshold OR isnull(@cooldown_time_threshold)) THEN
SET @creation = CONCAT('
UPDATE `',guild,'` 
SET lastmsg=CURRENT_TIME(), money=money+salary
WHERE ID=\'',chatter,'\';');
PREPARE test FROM @creation;
EXECUTE test;
DEALLOCATE PREPARE test; 
END IF;
END$$
delimiter ;