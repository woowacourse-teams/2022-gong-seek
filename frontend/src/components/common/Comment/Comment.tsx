import CommentInputModal from '../CommentInputModal/CommentInputModal';
import { useState } from 'react';

import * as S from '@/components/common/Comment/Comment.styles';
import Loading from '@/components/common/Loading/Loading';
import useDeleteComment from '@/components/common/Comment/hooks/useDeleteComment';

import { CommentType } from '@/types/commentResponse';

interface CommentProps extends CommentType {
	articleId: string;
}

const Comment = ({ id, author, content, createdAt, isAuthor, articleId }: CommentProps) => {
	const [isEditCommentOpen, setIsEditCommentOpen] = useState(false);
	const [commentPlaceholder, setCommentPlaceHolder] = useState('');
	const { isLoading, onDeleteButtonClick } = useDeleteComment();

	const onUpdateButtonClick = () => {
		setCommentPlaceHolder(content);
		setIsEditCommentOpen(true);
	};

	if (isLoading) return <Loading />;

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
						<S.Button
							onClick={() => {
								onDeleteButtonClick(id);
							}}
						>
							삭제
						</S.Button>
					</S.CommentAuthBox>
				)}
			</S.CommentHeader>
			<S.CommentContent>{content}</S.CommentContent>

			{isEditCommentOpen && (
				<>
					<S.DimmerContainer onClick={() => setIsEditCommentOpen(false)} />
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
