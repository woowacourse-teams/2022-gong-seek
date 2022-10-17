import { useState } from 'react';

import Loading from '@/components/@common/Loading/Loading';
import ToastUiViewer from '@/components/@common/ToastUiViewer/ToastUiViewer';
import * as S from '@/components/comment/Comment/Comment.styles';
import CommentInputModal from '@/components/comment/CommentInputModal/CommentInputModal';
import useDeleteComment from '@/hooks/comment/useDeleteComment';
import { CommentType } from '@/types/commentResponse';
import { convertGithubAvatarUrlForResize } from '@/utils/converter';
import { dateTimeConverter } from '@/utils/converter';

export interface CommentProps extends CommentType {
	articleId: string;
	tabIndex: number;
}

const Comment = ({
	id,
	author,
	content,
	createdAt,
	isAuthor,
	articleId,
	tabIndex,
}: CommentProps) => {
	const [isEditCommentOpen, setIsEditCommentOpen] = useState(false);
	const [commentPlaceholder, setCommentPlaceHolder] = useState('');
	const { isLoading, onDeleteButtonClick } = useDeleteComment();

	const onUpdateButtonClick = () => {
		setCommentPlaceHolder(content);
		setIsEditCommentOpen(true);
	};

	if (isLoading) return <Loading />;

	return (
		<S.Container tabIndex={tabIndex}>
			<S.CommentHeader>
				<S.CommentInfo>
					<S.UserProfile
						alt="작성자의 프로필"
						src={convertGithubAvatarUrlForResize(author.avatarUrl)}
						tabIndex={tabIndex}
					/>
					<S.CommentInfoSub>
						<S.UserName aria-label={`댓글 작성자 ${author.name}`} tabIndex={tabIndex}>
							{author.name}
						</S.UserName>
						<S.CreateTime
							aria-label={`댓글 작성시간 ${dateTimeConverter(createdAt)}`}
							tabIndex={tabIndex}
						>
							{dateTimeConverter(createdAt)}
						</S.CreateTime>
					</S.CommentInfoSub>
				</S.CommentInfo>
				{isAuthor && (
					<S.CommentAuthBox>
						<S.Button onClick={onUpdateButtonClick} tabIndex={tabIndex}>
							수정
						</S.Button>
						<S.Button
							onClick={() => {
								onDeleteButtonClick(id);
							}}
							tabIndex={tabIndex}
						>
							삭제
						</S.Button>
					</S.CommentAuthBox>
				)}
			</S.CommentHeader>
			<S.CommentContent tabIndex={tabIndex}>
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
