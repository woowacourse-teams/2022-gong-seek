import { getComments } from '@/api/comments';
import ArticleContent from '@/components/common/ArticleContent/ArticleContent';
import Comment from '@/components/common/Comment/Comment';
import CommentInputModal from '@/components/common/CommentInputModal/CommentInputModal';

import * as S from '@/pages/Detail/index.style';
import React, { useState } from 'react';
import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

const Detail = ({ children }: { children?: React.ReactNode }) => {
	//mock data
	const article = {
		title:
			'글 상세페이지에서의 글 제목이 들어가는 곳, 글 제목이 2줄 이상이 넘어갔을때 어떻게 처리할 것인지 처리하기 위한 예시 문장입니다',
		content:
			'글 상세페이지에서 글의 내용이 들어가는 곳, 이곳에서는 TextEditorViews가 보여지는 곳입니다',
		createAt: '5분전',
		views: 10,
		likeCount: 10,
	};

	const author = {
		name: '자스민',
		avatarUrl: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
	};

	const [isCommentOpen, setIsCommentOpen] = useState(false);
	const { id } = useParams();
	if (typeof id === 'undefined') {
		throw new Error('글을 찾지 못하였습니다.');
	}

	const { data, isLoading, isError } = useQuery('comments', () => getComments({ articleId: id }));

	if (isLoading) return <div>로딩중...</div>;

	if (isError) return <div>에러...!</div>;

	console.log(data);

	return (
		<S.Container>
			<ArticleContent
				article={article}
				author={author}
				isAuthor={true}
				category={children ? '토론' : '에러'}
			/>
			{children}

			{isLoading ? (
				<div>로딩중...</div>
			) : (
				<S.CommentSection>
					<S.CommentInputBox>
						<S.CommentInput
							aria-label="댓글을 입력하는 창으로 이동하는 링크 입니다"
							onClick={() => setIsCommentOpen(true)}
						/>
						<S.CreateCommentButton
							aria-label="댓글을 입력하는 창으로 이동하는 링크입니다."
							onClick={() => setIsCommentOpen(true)}
						/>
					</S.CommentInputBox>

					<S.CommentHeader>
						<S.CommentTitle>댓글</S.CommentTitle>
						<S.CommentTotal>
							<S.CommentIcon />
							<div>{data?.length || 0}개</div>
						</S.CommentTotal>
					</S.CommentHeader>

					{data?.map((item) => (
						<Comment
							key={item.id}
							id={item.id}
							articleId={id}
							authorName={item.authorName}
							authorAvartarUrl={item.authorAvartarUrl}
							content={item.content}
							createdAt={item.createdAt}
							isAuthor={item.isAuthor}
						/>
					))}
				</S.CommentSection>
			)}
			{isCommentOpen && (
				<>
					<S.DimmerContainer onClick={() => setIsCommentOpen(false)} />
					<CommentInputModal
						closeModal={() => setIsCommentOpen(false)}
						articleId={id}
						modalType="register"
					/>
				</>
			)}
		</S.Container>
	);
};

export default Detail;
