CREATE TABLE `tester` (
  `ID` varchar(25) NOT NULL,
  `money` int(11) NOT NULL,
  `level` tinyint(4) NOT NULL,
  `salary` tinyint(4) NOT NULL,
  `cooldown` tinyint(4) NOT NULL,
  `lastmsg` TIME,
  `daily` datetime,
  `total_msgs` mediumint(8) unsigned DEFAULT 0,
  PRIMARY KEY(`ID`)
);

INSERT INTO `tester`(`ID`, `money`, `level`, `cooldown`, `salary`) 
VALUES 
(197757126611959808,20,1,10,1),
(147757126211959808,20,1,10,1),
(197757126621559808,20,1,10,1);

DELETE FROM tester WHERE ID != '197757126611959808';

UPDATE tester SET lastmsg=TIME(CURRENT_TIME());

SELECT TIME(curtime());

select routine_name, routine_type, definer, created, security_type, SQL_Data_Access, ROUTINE_DEFINITION 
from information_schema.routines 
where definer!='mysql.sys@localhost';

CALL chatted("297757126611959808", "197757126621559808");

/*
USE FUNCTION FOR LEVEL UP AND SEND MSG ON JAVA SIDE BASED ON 1 or 0 RESPONCE
*/