import ArticleContent from '@/components/common/ArticleContent/ArticleContent';
import Comment from '@/components/common/Comment/Comment';
import CommentInputModal from '@/components/common/CommentInputModal/CommentInputModal';

import * as S from '@/pages/Detail/index.styles';
import { ArticleType } from '@/types/articleResponse';
import { CommentType } from '@/types/commentResponse';
import React, { useState } from 'react';
import { useParams } from 'react-router-dom';

interface DetailProps {
	children?: React.ReactNode;
	article: ArticleType;
	commentList: CommentType[];
	articleId: string;
}

const Detail = ({ children, article, commentList, articleId }: DetailProps) => {
	const [isCommentOpen, setIsCommentOpen] = useState(false);
	const { id } = useParams();
	if (typeof id === 'undefined') {
		throw new Error('글을 찾지 못하였습니다.');
	}
	return (
		<S.Container>
			<ArticleContent
				article={article}
				author={article.author}
				category={children ? '토론' : '에러'}
				articleId={articleId}
			/>
			{children}
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
						<div>{commentList.length || 0}개</div>
					</S.CommentTotal>
				</S.CommentHeader>
				{commentList &&
					commentList.length > 0 &&
					commentList.map((item) => (
						<Comment
							key={item.id}
							id={item.id}
							articleId={id}
							author={item.author}
							content={item.content}
							createdAt={item.createdAt}
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
						placeholder=""
					/>
				</>
			)}
		</S.Container>
	);
};

export default Detail;
