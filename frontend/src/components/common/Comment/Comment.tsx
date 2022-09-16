import { useState } from 'react';

import * as S from '@/components/common/Comment/Comment.styles';
import CommentInputModal from '@/components/common/CommentInputModal/CommentInputModal';
import Loading from '@/components/common/Loading/Loading';
import ToastUiViewer from '@/components/common/ToastUiViewer/ToastUiViewer';
import useDeleteComment from '@/hooks/comment/useDeleteComment';
import { CommentType } from '@/types/commentResponse';
import { dateTimeConverter } from '@/utils/converter';

export interface CommentProps extends CommentType {
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
						<S.CreateTime>{dateTimeConverter(createdAt)}</S.CreateTime>
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
			<S.CommentContent>
				<ToastUiViewer initContent={content} />
			</S.CommentContent>

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
