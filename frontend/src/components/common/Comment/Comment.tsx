import { deleteComments } from '@/api/comments';
import * as S from '@/components/common/Comment/Comment.style';
import styled from '@emotion/styled';
import { useEffect, useState } from 'react';
import { useMutation } from 'react-query';
import CommentInputModal from '../CommentInputModal/CommentInputModal';
import { queryClient } from '@/index';
import { CommentType } from '@/types/commentResponse';
export const DimmerContainer = styled.div`
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: ${({ theme }) => theme.colors.GRAY_500};
	z-index: 110;
`;

interface CommentProps extends CommentType {
	articleId: string;
}

const Comment = ({ id, author, content, createdAt, isAuthor, articleId }: CommentProps) => {
	const [isEditCommentOpen, setIsEditCommentOpen] = useState(false);
	const [commentPlaceholder, setCommentPlaceHolder] = useState('');
	const { isLoading, isError, isSuccess, mutate } = useMutation(deleteComments);

	const onUpdateButtonClick = () => {
		setCommentPlaceHolder(content);
		setIsEditCommentOpen(true);
	};

	const onDeleteButtonClick = () => {
		if (confirm('정말로 삭제하시겠습니까?')) {
			mutate({ commentId: String(id) });
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
					<S.UserProfile alt="유저 프로필이 보여지는 곳입니다" src={author.avatarUrl} />
					<S.CommentInfoSub>
						<S.UserName>{author.name}</S.UserName>
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
						placeholder={commentPlaceholder}
					/>
				</>
			)}
		</S.Container>
	);
};

export default Comment;
