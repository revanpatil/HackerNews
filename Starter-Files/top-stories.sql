-- -----------------------------------------------------
-- Schema hacker-news
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `hacker-news`;

CREATE SCHEMA `hacker-news`;
USE `hacker-news` ;

-- -----------------------------------------------------
-- Table `hacker-news`.`top-stories`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `hacker-news`.`topstories` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user` VARCHAR(255) DEFAULT NULL,
  `descendants` BIGINT(20) DEFAULT NULL,
  `story_id` BIGINT(20) DEFAULT NULL,
  `score` BIGINT(20) DEFAULT NULL,
  `time` BIGINT(20) DEFAULT NULL,
  `title` VARCHAR(255) DEFAULT NULL,
  `type` VARCHAR(255) DEFAULT NULL,
  `url` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
ENGINE=InnoDB
AUTO_INCREMENT = 1;

