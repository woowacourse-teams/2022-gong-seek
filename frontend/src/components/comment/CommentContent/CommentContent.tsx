import { Suspense } from 'react';

import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import Loading from '@/components/@common/Loading/Loading';
import Comment from '@/components/comment/Comment/Comment';
import * as S from '@/components/comment/CommentContent/CommentContent.styles';
import useDetailCommentState from '@/hooks/comment/useDetailCommentState';
import useGetDetailComment from '@/hooks/comment/useGetDetailComment';

export interface CommentContentProps {
	articleId: string;
}

const CommentContent = ({ articleId }: CommentContentProps) => {
	const { handleClickCommentPlusButton, isLogin } = useDetailCommentState({ articleId });
	const { data } = useGetDetailComment(articleId);

	return (
		<Suspense fallback={<Loading />}>
			<S.CommentSection>
				<S.CommentInputBox>
					<S.CommentInput
						aria-label="댓글을 입력하는 창으로 이동하는 링크 입니다"
						role="link"
						onClick={handleClickCommentPlusButton}
						disabled={!isLogin}
						tabIndex={0}
					/>
					<S.CreateCommentButton
						aria-label="댓글을 입력하는 창으로 이동하는 링크입니다."
						role="link"
						onClick={handleClickCommentPlusButton}
						disabled={!isLogin}
						tabIndex={0}
					/>
				</S.CommentInputBox>

				<S.CommentHeader>
					<S.CommentTitle>댓글</S.CommentTitle>
					<S.CommentTotal>
						<S.CommentIcon />
						<div aria-label={`댓글 갯수 ${data?.comments.length}`} tabIndex={0}>
							{data?.comments.length || 0}개
						</div>
					</S.CommentTotal>
				</S.CommentHeader>

				{data?.comments && data.comments.length > 0 ? (
					data.comments.map((item) => (
						<Comment
							key={item.id}
							id={item.id}
							articleId={articleId}
							author={item.author}
							content={item.content}
							createdAt={item.createdAt}
							isAuthor={item.isAuthor}
							tabIndex={0}
						/>
					))
				) : (
					<EmptyMessage>첫 번째 댓글을 달아주세요!</EmptyMessage>
				)}
			</S.CommentSection>
		</Suspense>
	);
};

export default CommentContent;
