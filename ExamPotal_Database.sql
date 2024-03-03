create database exam_project;
use exam_project;
-- ROLE TABLE --
CREATE TABLE `role` (
	`roleId` BIGINT AUTO_INCREMENT PRIMARY KEY,
	`roleName` VARCHAR(50) NOT NULL
);
INSERT INTO `exam_project`.`role` (`roleId`, `roleName`) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `exam_project`.`role` (`roleId`, `roleName`) VALUES ('2', 'ROLE_STUDENT');
INSERT INTO `exam_project`.`role` (`roleId`, `roleName`) VALUES ('3', 'ROLE_TEACHER');

-- USER TABLE --
CREATE TABLE `user` (
	`userId` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(50),
	`username` VARCHAR(50) NOT NULL,
	`password` VARCHAR(100) NOT NULL,
	`email` VARCHAR(100) NOT NULL,
    `roleId` BIGINT,
    FOREIGN KEY (`roleId`) REFERENCES `role`(`roleId`)
);

-- CATEGORY ROLE --
CREATE TABLE `Category` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `createBy` BIGINT,
  FOREIGN KEY (`createBy`) REFERENCES `user`(`userId`)
);

-- QUIZ TABLE --
CREATE TABLE `Quiz` (
  `quizId` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `maxMarks` INT NOT NULL,
  `numberOfQuestions` INT NOT NULL,
  `active` BOOLEAN DEFAULT FALSE,
  `categoryId` BIGINT,
  FOREIGN KEY (`categoryId`) REFERENCES `Category`(`id`)
);

-- HISTORY TABLE --
CREATE TABLE `user_quiz_history` (
  `user_id` BIGINT,
  `quiz_id` BIGINT,
  PRIMARY KEY (user_id, quiz_id),
  FOREIGN KEY (user_id) REFERENCES `User`(userId),
  FOREIGN KEY (quiz_id) REFERENCES `Quiz`(quizId)
);

-- QUESTION TYPE TABLE --
CREATE TABLE `Question_Type` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `displayName` VARCHAR(50) NOT NULL	
);
INSERT INTO `exam_project`.`question_type` (`id`, `displayName`) VALUES ('1', 'Trắc nghiệm');
INSERT INTO `exam_project`.`question_type` (`id`, `displayName`) VALUES ('2', 'Tự luận');


-- QUESTION TABLE --
CREATE TABLE `Question` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `content` BLOB NULL, -- tk này dùng cho câu trả lời dạng hình ảnh or âm thanh
  `text_content` TEXT NULL, -- tk này thì dùng cho dạng văn bản
  `type_id` BIGINT,
  `quiz_id` BIGINT,
  FOREIGN KEY (`type_id`) REFERENCES `Question_Type`(`id`),
  FOREIGN KEY (`quiz_id`) REFERENCES `Quiz`(`quizId`)
);

-- ANSWER TABLE --
CREATE TABLE `Answer` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `content` BLOB NOT NULL, -- tk này dùng cho câu trả lời dạng hình ảnh or âm thanh
  `text_content` TEXT NOT NULL, -- tk này thì dùng cho dạng văn bản
  `isCorrect` BOOLEAN NOT NULL,
  `question_id` BIGINT,
  FOREIGN KEY (question_id) REFERENCES Question(id)
);





