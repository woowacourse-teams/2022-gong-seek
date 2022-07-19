import { getComments } from '@/api/comments';
import ArticleContent from '@/components/common/ArticleContent/ArticleContent';
import Comment from '@/components/common/Comment/Comment';
import CommentInputModal from '@/components/common/CommentInputModal/CommentInputModal';

import * as S from '@/pages/Detail/index.style';
import { ArticleType } from '@/types/articleResponse';
import { Author } from '@/types/author';
import { CommentType } from '@/types/commentResponse';
import React, { useState } from 'react';
import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

interface DetailProps {
	children?: React.ReactNode;
	article: ArticleType;
	commentList: CommentType[];
}

const Detail = ({ children, article, commentList }: DetailProps) => {
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
				author={article.author}
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

				{commentList.map((item) => (
					<Comment
						key={item.id}
						id={item.id}
						author={item.author}
						content={item.content}
						createAt={item.createdAt}
						isAuthor={item.isAuthor}
					/>
				))}
			</S.CommentSection>
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
