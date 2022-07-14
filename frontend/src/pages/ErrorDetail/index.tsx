import ArticleContent from '@/components/common/ArticleContent/ArticleContent';
import Comment from '@/components/common/Comment/Comment';

import * as S from '@/pages/ErrorDetail/index.style';

const ErrorDetail = () => {
	//mock data
	const article = {
		title:
			'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
		content:
			'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
		createAt: '5분전',
		views: '10',
		likeCount: '10',
	};

	const author = {
		name: '자스민',
		avatar: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
	};

	const commentList = [
		{
			id: '1',
			author: {
				name: '자스민',
				avatar: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
			},
			content:
				'댓글 예시를 적는 곳입니다. 댓글 예시입니다. 2줄 이상일때에 어떻게 처리할지 다루기 위해서 입력하는 곳입니다. 안녕하세요 공식을 방문해주셔서 감사합니다. 실험 테스트 중입니다. 에헿에에에헹헤에에헤엫ㅇ헿',
			createAt: '2022.07.08:19:03',
			isAuthor: false,
		},
		{
			id: '2',
			author: {
				name: '자스민',
				avatar: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
			},
			content:
				'댓글 예시를 적는 곳입니다. 댓글 예시입니다. 2줄 이상일때에 어떻게 처리할지 다루기 위해서 입력하는 곳입니다. 안녕하세요 공식을 방문해주셔서 감사합니다. 실험 테스트 중입니다. 에헿에에에헹헤에에헤엫ㅇ헿',
			createAt: '2022.07.08:19:03',
			isAuthor: true,
		},
		{
			id: '3',
			author: {
				name: '자스민',
				avatar: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
			},
			content:
				'댓글 예시를 적는 곳입니다. 댓글 예시입니다. 2줄 이상일때에 어떻게 처리할지 다루기 위해서 입력하는 곳입니다. 안녕하세요 공식을 방문해주셔서 감사합니다. 실험 테스트 중입니다. 에헿에에에헹헤에에헤엫ㅇ헿',
			createAt: '2022.07.08:19:03',
			isAuthor: false,
		},
		{
			id: '4',
			author: {
				name: '자스민',
				avatar: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
			},
			content:
				'댓글 예시를 적는 곳입니다. 댓글 예시입니다. 2줄 이상일때에 어떻게 처리할지 다루기 위해서 입력하는 곳입니다. 안녕하세요 공식을 방문해주셔서 감사합니다. 실험 테스트 중입니다. 에헿에에에헹헤에에헤엫ㅇ헿',
			createAt: '2022.07.08:19:03',
			isAuthor: false,
		},
		{
			id: '5',
			author: {
				name: '자스민',
				avatar: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
			},
			content:
				'댓글 예시를 적는 곳입니다. 댓글 예시입니다. 2줄 이상일때에 어떻게 처리할지 다루기 위해서 입력하는 곳입니다. 안녕하세요 공식을 방문해주셔서 감사합니다. 실험 테스트 중입니다. 에헿에에에헹헤에에헤엫ㅇ헿',
			createAt: '2022.07.08:19:03',
			isAuthor: false,
		},
	];

	return (
		<S.Container>
			<ArticleContent article={article} author={author} isAuthor={true} category="에러" />
			<S.CommentSection>
				<S.CommentHeader>
					<S.CommentTitle>댓글</S.CommentTitle>
					<S.CommentTotal>
						<S.CommentIcon />
						<div>5개</div>
					</S.CommentTotal>
				</S.CommentHeader>
				{commentList.map((item) => (
					<Comment
						key={item.id}
						id={item.id}
						author={item.author}
						content={item.content}
						createAt={item.createAt}
						isAuthor={item.isAuthor}
					/>
				))}
			</S.CommentSection>
		</S.Container>
	);
};

export default ErrorDetail;
