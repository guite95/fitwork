# 회원정보
INSERT INTO member (id, password, name, nickname, gender, birth_date, email, phone_number, address, member_role) VALUES
('kimminsu94', '1234', '김민수', 'minsoo01', 'M', '1994-05-12', 'minsoo01@gmail.com', '010-1234-5678', '서울특별시 강남구 테헤란로 123', 'ROLE_USER'),
('parkjihyun92', '1234', '박지현', 'jihyun02', 'F', '1992-11-23', 'jihyun02@gmail.com', '010-2345-6789', '대전광역시 유성구 대학로 99', 'ROLE_INSTRUCTOR'),
('leejunho97', '1234', '이준호', 'junho03', 'M', '1997-08-30', 'junho03@gmail.com', '010-3456-7890', '부산광역시 해운대구 센텀중앙로 45', 'ROLE_USER'),
('choisooji93', '1234', '최수지', 'soojee04', 'F', '1993-02-14', 'soojee04@gmail.com', '010-4567-8901', '서울특별시 종로구 세종대로 12', 'ROLE_USER'),
('junghana96', '1234', '정하나', 'hana05', 'F', '1996-09-05', 'hana05@gmail.com', '010-5678-9012', '대전광역시 서구 둔산로 123', 'ROLE_INSTRUCTOR'),
('kangdonghyun99', '1234', '강동현', 'donghyun06', 'M', '1999-06-17', 'donghyun06@gmail.com', '010-6789-0123', '부산광역시 남구 문현로 78', 'ROLE_USER'),
('ohyounghee91', '1234', '오영희', 'younghee07', 'F', '1991-12-09', 'younghee07@gmail.com', '010-7890-1234', '서울특별시 성북구 동소문로 56', 'ROLE_INSTRUCTOR'),
('kimjihoon95', '1234', '김지훈', 'jihoon08', 'M', '1995-03-22', 'jihoon08@gmail.com', '010-8901-2345', '대전광역시 동구 중앙로 77', 'ROLE_USER'),
('hanyesul98', '1234', '한예슬', 'yesul09', 'F', '1998-07-19', 'yesul09@gmail.com', '010-9012-3456', '부산광역시 수영구 광안로 88', 'ROLE_INSTRUCTOR'),
('jominjae00', '1234', '조민재', 'minjae10', 'M', '2000-10-11', 'minjae10@gmail.com', '010-0123-4567', '서울특별시 마포구 양화로 23', 'ROLE_USER');


# 게시글 정보
INSERT INTO board (title, writer, category, content, reg_date, view_cnt, like_cnt) VALUES
('부산 운동 모임 후기', 'junho03', 'club', '와 여러분 지난주에 부산 운동 모임 갔었는데 진짜 너무 즐거웠어요! 다들 힘들긴 했지만 함께 운동하고 맛있는 것도 먹고 너무 행복했어요. 다음에도 꼭 함께해요!', '2024-07-15 14:30:00', 340, 67),
('요가 클래스 수강 후기', 'jihyun02', 'class', '이번에 요가 클래스 들어봤는데요, 확실히 몸이 유연해지는 느낌이에요! 선생님도 너무 친절하시고 분위기 짱이었어요. 다들 스트레칭도 같이 해봐요!', '2024-08-05 10:00:00', 420, 55),
('운동 모임, 이거 진짜 대박!', 'younghee07', 'club', '운동 모임 참여하고 나서 확실히 제 삶의 활력이 생긴 것 같아요! 매번 새로운 사람들과 운동하는 게 이렇게 재밌을 줄이야! 여러분도 한 번 참여해보세요!', '2024-09-10 18:45:00', 275, 72),
('일상 잡담: 요즘 운동 루틴', 'soojee04', 'chat', '요즘 매일 아침 6시에 일어나서 조깅 시작했어요. 초반엔 힘들었는데 지금은 완전 상쾌하고 하루 시작이 훨씬 좋아요. 다들 조깅 해보세요!', '2024-07-22 07:30:00', 310, 34),
('클라이밍 클래스 후기', 'hana05', 'class', '클라이밍 진짜 신나요! 무서웠지만 도전 정신도 생기고, 선생님이 격려 많이 해주셔서 끝까지 해낼 수 있었어요. 한 번 해보시면 절대 후회 안해요!', '2024-08-25 13:20:00', 490, 89),
('부산에서의 운동 모임', 'donghyun06', 'club', '부산에서 운동 모임이 있었는데 너무 재밌었어요. 날씨도 좋고 모임 사람들도 다들 친절해서 즐거웠네요. 다음에는 더 많은 분들이 와주셨으면 해요!', '2024-10-02 16:00:00', 360, 45),
('필라테스 클래스 후기', 'yesul09', 'class', '필라테스 이번에 처음 해봤는데 생각보다 힘들더라구요! 근데 끝나고 나면 몸이 진짜 개운해요. 여러분도 필라테스 한 번씩 꼭 해보세요~', '2024-09-20 11:15:00', 380, 64),
('일상 잡담: 걷기 운동', 'minjae10', 'chat', '요즘 날씨 너무 좋아서 걷기 운동하기 딱이네요. 서울숲에서 걷기 하고 왔는데 기분도 좋고 몸도 개운해서 추천드려요!', '2024-10-10 09:00:00', 260, 20),
('런닝 모임 후기', 'minsoo01', 'club', '런닝 모임 다녀왔어요! 진짜 너무 힘들었지만 그만큼 성취감이 장난 아니었어요. 같이 달린 분들 모두 고생하셨습니다!', '2024-11-01 17:30:00', 415, 80),
('헬스장 클래스 수강 후기', 'jihoon08', 'class', '헬스장에서 체력 강화 클래스 수강했는데, 진짜 땀 많이 흘리고 성취감도 최고였어요. 다들 건강 챙겨요!', '2024-11-08 19:00:00', 500, 92),
('서울 운동 모임 후기', 'junho03', 'club', '서울에서 운동 모임 했는데 너무 좋았어요! 함께한 사람들이 다들 열정적이라 정말 즐거운 시간이었어요!', '2024-07-20 15:00:00', 330, 70),
('필라테스 클래스 추천', 'soojee04', 'class', '필라테스 클래스 들었는데 정말 도움이 많이 되었어요! 몸도 가벼워지고 기분도 좋아졌어요. 추천합니다!', '2024-07-25 11:00:00', 290, 40),
('부산 모임 후기', 'younghee07', 'club', '부산 모임 정말 즐거웠어요! 다들 함께 운동하고 이야기 나누는 시간이 너무 좋았어요.', '2024-08-15 16:00:00', 450, 88),
('요가 클래스 참여 후기', 'jihyun02', 'class', '요가 클래스 너무 좋았어요! 몸도 유연해지고 스트레스도 풀렸어요. 여러분도 한 번 해보세요!', '2024-09-01 09:00:00', 375, 55),
('등산 모임 후기', 'donghyun06', 'club', '주말에 등산 모임 갔는데 너무 좋았어요! 자연 속에서 함께한 시간이 너무 소중했어요!', '2024-09-15 08:00:00', 310, 60),
('헬스장 루틴 공유', 'jihoon08', 'chat', '요즘 헬스장에서 하는 루틴 공유합니다. 아침마다 운동하는 게 정말 도움이 돼요!', '2024-10-05 06:00:00', 280, 25),
('클라이밍 첫 도전 후기', 'minjae10', 'class', '클라이밍 처음 해봤는데 정말 재밌었어요! 무서웠지만 도전해볼 가치가 있었어요!', '2024-10-18 14:00:00', 400, 70),
('운동 모임 후기', 'yesul09', 'club', '이번 운동 모임도 대성공! 정말 좋은 사람들과 함께해서 너무 즐거웠어요. 다음에도 꼭 참석할게요!', '2024-11-05 15:30:00', 390, 85),
('서울에서의 운동', 'minjae10', 'club', '서울에서 운동 모임 했는데 날씨도 좋고 함께한 분들도 모두 최고였어요!', '2024-11-09 10:00:00', 370, 65),
('요가 클래스 후기', 'hana05', 'class', '요가 수업 너무 좋았어요! 유연성도 늘고 기분도 좋아졌어요. 강사님이 너무 잘 가르쳐주셔서 더욱 좋았네요.', '2024-08-10 10:00:00', 400, 75),
('자전거 타기 후기', 'donghyun06', 'club', '자전거 타기 모임 참석했는데 너무 즐거웠어요! 다들 함께해서 더 좋았던 것 같아요.', '2024-07-30 17:00:00', 410, 50),
('아침 운동 루틴 공유', 'soojee04', 'chat', '매일 아침 스트레칭과 조깅으로 하루를 시작하고 있어요. 여러분도 한번 해보세요!', '2024-09-25 06:00:00', 350, 40),
('필라테스 수업 후기', 'younghee07', 'class', '필라테스 수업 들었는데 생각보다 훨씬 좋았어요! 몸이 개운하고 유연해진 느낌입니다.', '2024-09-28 11:00:00', 320, 60),
('운동 모임 후기', 'jihoon08', 'club', '이번 운동 모임도 정말 좋았습니다. 다들 열심히 운동하고 웃으며 보낸 시간들이 잊혀지지 않네요!', '2024-10-12 16:30:00', 390, 72),
('서울에서의 걷기 운동', 'minsoo01', 'chat', '서울 한강공원에서 걷기 운동 했는데 날씨가 좋아서 정말 상쾌했습니다. 추천드려요!', '2024-10-16 08:30:00', 280, 30),
('헬스장 수업 후기', 'yesul09', 'class', '헬스장에서 새로운 수업 들어봤는데 너무 좋았어요. 체력도 늘고 기분도 상쾌해졌습니다!', '2024-10-20 18:00:00', 470, 90),
('부산 운동 모임 후기', 'donghyun06', 'club', '부산에서의 운동 모임이 최고였습니다. 다들 열심히 운동하고 즐거운 시간이었습니다.', '2024-11-03 15:00:00', 420, 85),
('클라이밍 도전 후기', 'hana05', 'class', '클라이밍 도전해봤는데 처음엔 무서웠지만 정말 재밌었어요. 한 번쯤은 꼭 해보세요!', '2024-11-06 14:00:00', 450, 80),
('운동 모임 후기', 'minjae10', 'club', '모두 함께 운동하고 소통하는 모임이 너무 좋았습니다. 다시 한번 꼭 참여하고 싶어요!', '2024-11-07 13:00:00', 360, 78),
('일상 잡담: 운동 루틴', 'younghee07', 'chat', '요즘 매일 아침 스트레칭을 하고 있는데 몸이 한결 개운해요. 추천합니다!', '2024-08-18 07:00:00', 310, 45);

#게시글에 대한 댓글 정보
INSERT INTO comment (writer, content, reg_date, board_no) VALUES
('jihyun02', '정말 즐거웠을 것 같아요! 다음에도 저도 꼭 참여할게요!', '2024-07-16 09:45:00', 1),
('jihyun02', '운동 후에 먹는 맛있는 음식은 최고죠~ 다음에 꼭 가보고 싶네요!', '2024-07-17 12:30:00', 1),
('soojee04', '부산 운동 모임이라니! 다음에 꼭 참여하고 싶어요!', '2024-07-18 09:30:00', 1),
('jihyun02', '운동 후에 먹는 음식이 정말 꿀맛이죠!', '2024-07-19 12:00:00', 1),
('soojee04', '부산 모임 너무 재미있었어요! 다들 너무 친절하고 좋았어요!', '2024-07-20 16:10:00', 2),
('hana05', '운동 모임 너무 즐거웠어요. 다음에는 더 많은 분들과 함께 하고 싶네요!', '2024-07-21 11:25:00', 2),
('donghyun06', '부산에서 다들 정말 재미있었어요! 다음에도 꼭 함께해요!', '2024-07-22 14:00:00', 2),
('jihyun02', '아침 조깅 너무 좋아요! 저도 꼭 따라해봐야겠어요~', '2024-07-23 08:20:00', 5),
('donghyun06', '아침에 운동하면 하루가 더 상쾌하게 시작되는 것 같아요!', '2024-07-24 07:50:00', 5),
('hana05', '아침 운동으로 하루를 시작하는 것이 정말 좋아요!', '2024-07-25 06:50:00', 5),
('donghyun06', '아침 운동 너무 상쾌해요. 다들 함께하면 더 좋을 것 같아요!', '2024-07-26 07:20:00', 5),
('soojee04', '필라테스 너무 재미있었어요! 다들 함께해서 더욱 좋았네요.', '2024-07-26 14:30:00', 6),
('yesul09', '필라테스 하고 나니 몸이 가벼워졌어요! 추천합니다!', '2024-07-27 10:00:00', 6),
('yesul09', '필라테스 클래스 들었는데 정말 좋았어요. 몸이 가벼워지는 느낌!', '2024-07-28 13:00:00', 6),
('donghyun06', '요가 수업에서 스트레스 풀고 몸이 개운해졌어요! 강사님도 너무 친절하시고요!', '2024-08-06 15:50:00', 3),
('yesul09', '요가 끝나고 몸이 정말 가벼워진 것 같아요. 같이 운동하실 분 구해요!', '2024-08-07 10:20:00', 3),
('jihyun02', '요가 클래스 너무 좋았어요! 몸이 유연해지는 걸 느꼈어요!', '2024-08-08 09:15:00', 3),
('soojee04', '다음에도 이런 모임이 있다면 꼭 참여하고 싶네요!', '2024-08-10 15:30:00', 1),
('jihyun02', '새로운 사람들과의 운동 너무 즐거웠어요!', '2024-08-11 10:10:00', 1),
('soojee04', '이번 운동 모임 정말 잊지 못할 시간이었어요!', '2024-08-12 16:20:00', 1),
('jihoon08', '클라이밍 도전 정말 대단해요! 저도 언젠가 해봐야겠어요.', '2024-08-15 13:30:00', 10),
('hana05', '요가 수업 덕분에 스트레칭도 많이 하고 좋았어요!', '2024-08-09 14:45:00', 3),
('hana05', '부산 모임 정말 좋았어요! 또 가고 싶어요.', '2024-08-20 10:15:00', 2),
('donghyun06', '아침 조깅이 이렇게 상쾌할 줄 몰랐어요. 다들 함께해요!', '2024-09-01 07:45:00', 5),
('jihyun02', '요가 수업 정말 좋았어요. 강사님이 최고예요!', '2024-09-03 09:30:00', 3),
('minjae10', '운동 모임의 활력 덕분에 저도 더 열심히 하게 되는 것 같아요!', '2024-09-11 13:40:00', 4),
('donghyun06', '클라이밍은 항상 도전할 가치가 있는 것 같아요!', '2024-09-11 16:00:00', 10),
('soojee04', '새로운 사람들과 함께 운동하는 건 언제나 좋은 것 같아요!', '2024-09-12 09:10:00', 4),
('soojee04', '운동 모임에서 너무 많은 에너지를 얻었어요!', '2024-09-12 11:15:00', 4),
('minjae10', '등산 모임 너무 재밌었어요. 다들 고생 많으셨어요!', '2024-09-16 08:30:00', 4),
('jihoon08', '필라테스 후유증 덕분에 더 운동 열심히 해야겠어요!', '2024-09-29 12:20:00', 6),
('donghyun06', '부산에서의 운동 모임 정말 잊을 수 없는 시간이었어요!', '2024-10-03 18:00:00', 7),
('jihyun02', '다음 모임이 너무 기대돼요!', '2024-10-01 09:30:00', 9),
('jihoon08', '다음에도 부산 모임 꼭 참석할게요! 모두 너무 친절하셨어요!', '2024-10-04 17:20:00', 7),
('jihyun02', '등산 모임 덕분에 너무 많은 에너지를 얻었어요!', '2024-10-05 12:20:00', 4),
('soojee04', '요즘 걷기 운동 정말 좋죠~ 저도 함께 걷고 싶어요!', '2024-10-11 10:15:00', 8),
('jihyun02', '서울숲에서 걷기 진짜 좋죠! 날씨 좋을 때 다들 꼭 해보세요!', '2024-10-12 08:50:00', 8),
('yesul09', '요가 정말 최고예요!', '2024-10-15 13:45:00', 3),
('minjae10', '등산 모임 덕분에 새로 태어난 기분이었어요!', '2024-10-18 09:50:00', 4),
('hana05', '필라테스 클래스는 저한테 꼭 필요했던 시간이었어요.', '2024-10-20 11:45:00', 6),
('jihoon08', '런닝 모임 너무 힘들었지만 그만큼 성취감도 컸어요!', '2024-11-02 11:30:00', 9),
('donghyun06', '다들 너무 열심히 뛰어서 좋았어요! 다음에 또 함께해요!', '2024-11-03 13:10:00', 9),
('jihoon08', '런닝 모임에서 진짜 많은 힘을 얻었습니다!', '2024-11-05 16:50:00', 9),
('donghyun06', '모두와 함께해서 정말 즐거웠어요!', '2024-11-06 12:40:00', 9),
('hana05', '클라이밍 도전이라니 멋져요! 저도 한번 도전해볼게요!', '2024-11-07 14:00:00', 10),
('minjae10', '무섭긴 했지만 끝나고 나니 너무 뿌듯했어요. 다음에도 도전해봐요!', '2024-11-08 10:45:00', 10),
('yesul09', '필라테스 강사님이 너무 잘 가르쳐주셔서 너무 좋았어요!', '2024-11-09 11:30:00', 6);


#클래스 더미
INSERT INTO class (leader, class_name, tag, location, description, price) VALUES
('귤', '테스트', '#20대', '서울시 강남구', 'ㅇㅇ', 1);
