import { useState } from 'react';

import Loading from '@/components/@common/Loading/Loading';
import ToastUiViewer from '@/components/@common/ToastUiViewer/ToastUiViewer';
import * as S from '@/components/comment/Comment/Comment.styles';
import useDeleteComment from '@/hooks/comment/useDeleteComment';
import useModal from '@/hooks/common/useModal';
import { CommentType } from '@/types/commentResponse';
import { convertGithubAvatarUrlForResize } from '@/utils/converter';
import { dateTimeConverter } from '@/utils/converter';

export interface CommentProps extends CommentType {
	articleId: string;
}

const Comment = ({ id, author, content, createdAt, isAuthor, articleId }: CommentProps) => {
	const [commentPlaceholder, setCommentPlaceHolder] = useState('');
	const { isLoading, onDeleteButtonClick } = useDeleteComment();
	const { showModal } = useModal();

	const onUpdateButtonClick = () => {
		setCommentPlaceHolder(content);
		showModal({
			modalType: 'comment-modal',
			modalProps: {
				articleId: String(articleId),
				modalType: 'edit',
				commentId: String(id),
				placeholder: commentPlaceholder,
			},
		});
	};

	if (isLoading) return <Loading />;

	return (
		<S.Container>
			<S.CommentHeader>
				<S.CommentInfo>
					<S.UserProfile
						alt="유저 프로필이 보여지는 곳입니다"
						src={convertGithubAvatarUrlForResize(author.avatarUrl)}
					/>
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
		</S.Container>
	);
};

export default Comment;
