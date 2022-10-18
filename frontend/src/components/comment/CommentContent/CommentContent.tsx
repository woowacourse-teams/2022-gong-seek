import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import Comment from '@/components/comment/Comment/Comment';
import * as S from '@/components/comment/CommentContent/CommentContent.styles';
import CommentInputModal from '@/components/comment/CommentInputModal/CommentInputModal';
import useDetailCommentState from '@/hooks/comment/useDetailCommentState';
import { CommentType } from '@/types/commentResponse';

export interface CommentContentProps {
	articleId: string;
	commentList: CommentType[];
}

const CommentContent = ({ articleId, commentList }: CommentContentProps) => {
	const { handleCommentPlusButton, isLogin, isCommentOpen, setIsCommentOpen } =
		useDetailCommentState();
	return (
		<>
			<S.CommentSection>
				<S.CommentInputBox>
					<S.CommentInput
						aria-label="댓글을 입력하는 창으로 이동하는 링크 입니다"
						onClick={handleCommentPlusButton}
						disabled={!isLogin}
					/>
					<S.CreateCommentButton
						aria-label="댓글을 입력하는 창으로 이동하는 링크입니다."
						onClick={handleCommentPlusButton}
						disabled={!isLogin}
					/>
				</S.CommentInputBox>

				<S.CommentHeader>
					<S.CommentTitle>댓글</S.CommentTitle>
					<S.CommentTotal>
						<S.CommentIcon />
						<div>{commentList.length || 0}개</div>
					</S.CommentTotal>
				</S.CommentHeader>

				{commentList && commentList.length > 0 ? (
					commentList.map((item) => (
						<Comment
							key={item.id}
							id={item.id}
							articleId={articleId}
							author={item.author}
							content={item.content}
							createdAt={item.createdAt}
							isAuthor={item.isAuthor}
						/>
					))
				) : (
					<EmptyMessage>첫 번째 댓글을 달아주세요!</EmptyMessage>
				)}
			</S.CommentSection>
			{isCommentOpen && (
				<>
					<S.DimmerContainer onClick={() => setIsCommentOpen(false)} />
					<CommentInputModal
						closeModal={() => setIsCommentOpen(false)}
						articleId={articleId}
						modalType="register"
						placeholder=""
					/>
				</>
			)}
		</>
	);
};

export default CommentContent;
