-- -----------------------------------------------------
-- Table `hacker-news`.`past-stories`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `hacker-news`.`paststories`(
`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
`story_id` BIGINT(20) NOT NULL,
`title` VARCHAR(255) DEFAULT NULL,
`url` VARCHAR(255) DEFAULT NULL,
`time` BIGINT(20) DEFAULT NULL,
PRIMARY KEY(`id`)
)

ENGINE= InnoDB
AUTO_INCREMENT = 1
