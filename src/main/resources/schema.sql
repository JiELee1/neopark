-- 1. 공연장 테이블 생성 (stadium)
CREATE TABLE IF NOT EXISTS stadium (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255),
                                       address TEXT,
                                       image_url VARCHAR(255)
);

-- 2. 사용자 테이블 생성 (user)
CREATE TABLE IF NOT EXISTS user (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    social_domain VARCHAR(100),
                                    social_id VARCHAR(100),
                                    refresh_token VARCHAR(255),
                                    nickname VARCHAR(100),
                                    birth DATE,
                                    is_deleted TINYINT(1) DEFAULT 0,
                                    email VARCHAR(255)
);

-- 3. 좌석 등급 테이블 생성 (seat_grade)
CREATE TABLE IF NOT EXISTS seat_grade (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(50),
                                          price DECIMAL(10,2)
);

-- 4. 뮤지컬 카테고리 테이블 생성 (musical_category)
CREATE TABLE IF NOT EXISTS musical_category (
                                                id INT AUTO_INCREMENT PRIMARY KEY,
                                                name VARCHAR(100) NOT NULL
);

-- 5. 뮤지컬 테이블 생성 (musical)
CREATE TABLE IF NOT EXISTS musical (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       stadium_id INT,
                                       user_id INT,
                                       musical_category_id INT,
                                       title VARCHAR(255) NOT NULL,
                                       rating VARCHAR(50),
                                       thumbnail_url VARCHAR(255),
                                       genre VARCHAR(100),
                                       description TEXT,
                                       start_date DATE,
                                       end_date DATE,
                                       running_time INT,
                                       is_deleted TINYINT(1) DEFAULT 0,
                                       view_rating INT DEFAULT 0,
                                       FOREIGN KEY (stadium_id) REFERENCES stadium(id),
                                       FOREIGN KEY (user_id) REFERENCES user(id),
                                       FOREIGN KEY (musical_category_id) REFERENCES musical_category(id)
);

-- 6. 스케줄 테이블 생성 (schedule)
CREATE TABLE IF NOT EXISTS schedule (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        musical_id INT,
                                        start_time DATETIME,
                                        end_time DATETIME,
                                        is_deleted TINYINT(1) DEFAULT 0,
                                        FOREIGN KEY (musical_id) REFERENCES musical(id)
);

-- 7. 공연 좌석 테이블 생성 (musical_seat)
CREATE TABLE IF NOT EXISTS musical_seat (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            musical_id INT,
                                            schedule_id INT,
                                            seat_grade_id INT,
                                            is_reserved TINYINT(1) DEFAULT 0,
                                            FOREIGN KEY (musical_id) REFERENCES musical(id),
                                            FOREIGN KEY (schedule_id) REFERENCES schedule(id),
                                            FOREIGN KEY (seat_grade_id) REFERENCES seat_grade(id)
);

-- 8. 티켓 테이블 생성 (ticket)
CREATE TABLE IF NOT EXISTS ticket (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      schedule_id INT,
                                      seat_id INT,
                                      musical_id INT,
                                      stadium_id INT,
                                      status VARCHAR(50),
                                      FOREIGN KEY (schedule_id) REFERENCES schedule(id),
                                      FOREIGN KEY (seat_id) REFERENCES musical_seat(id),
                                      FOREIGN KEY (musical_id) REFERENCES musical(id),
                                      FOREIGN KEY (stadium_id) REFERENCES stadium(id)
);

-- 9. 배우 테이블 생성 (actor)
CREATE TABLE IF NOT EXISTS actor (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255),
                                     birth DATE,
                                     gender VARCHAR(10),
                                     image_url VARCHAR(255)
);

-- 10. 캐스팅 테이블 생성 (casting)
CREATE TABLE IF NOT EXISTS casting (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       musical_id INT,
                                       actor_id INT,
                                       field VARCHAR(100),
                                       FOREIGN KEY (musical_id) REFERENCES musical(id),
                                       FOREIGN KEY (actor_id) REFERENCES actor(id)
);

-- 11. 상세 이미지 테이블 생성 (musical_detail_image)
CREATE TABLE IF NOT EXISTS musical_detail_image (
                                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                                    musical_id INT,
                                                    image_url VARCHAR(255),
                                                    FOREIGN KEY (musical_id) REFERENCES musical(id)
);

