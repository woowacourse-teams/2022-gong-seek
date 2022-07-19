import { CommentsResponse, deleteComments } from '@/api/comments';
import * as S from '@/components/common/Comment/Comment.style';
import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import { useMutation } from 'react-query';
import CommentInputModal from '../CommentInputModal/CommentInputModal';
import { queryClient } from '@/index';
export const DimmerContainer = styled.div`
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: ${({ theme }) => theme.colors.GRAY_500};
	z-index: 110;
`;

interface CommentProps extends CommentsResponse {
	articleId: string;
}

const Comment = ({
	id,
	authorName,
	authorAvartarUrl,
	content,
	createdAt,
	isAuthor,
	articleId,
}: CommentProps) => {
	const [isEditCommentOpen, setIsEditCommentOpen] = useState(false);
	const { isLoading, isError, isSuccess, mutate } = useMutation(deleteComments);

	const onUpdateButtonClick = () => {
		setIsEditCommentOpen(true);
	};

	const onDeleteButtonClick = () => {
		if (confirm('정말로 삭제하시겠습니까?')) {
			mutate({ commentId: String(id), articleId });
		}
	};

	useEffect(() => {
		if (isSuccess) {
			queryClient.refetchQueries('comments');
		}
	}, [isSuccess]);

	if (isLoading) return <div>로딩중...</div>;

	if (isError) return <div>에러...!</div>;

	return (
		<S.Container>
			<S.CommentHeader>
				<S.CommentInfo>
					<S.UserProfile src={authorAvartarUrl} />
					<S.CommentInfoSub>
						<S.UserName>{authorName}</S.UserName>
						<S.CreateTime>{createdAt}</S.CreateTime>
					</S.CommentInfoSub>
				</S.CommentInfo>
				{isAuthor && (
					<S.CommentAuthBox>
						<S.Button onClick={onUpdateButtonClick}>수정</S.Button>
						<S.Button onClick={onDeleteButtonClick}>삭제</S.Button>
					</S.CommentAuthBox>
				)}
			</S.CommentHeader>
			<S.CommentContent>{content}</S.CommentContent>

			{isEditCommentOpen && (
				<>
					<DimmerContainer onClick={() => setIsEditCommentOpen(false)} />
					<CommentInputModal
						closeModal={() => setIsEditCommentOpen(false)}
						articleId={String(articleId)}
						modalType="edit"
						commentId={String(id)}
					/>
				</>
			)}
		</S.Container>
	);
};

export default Comment;
