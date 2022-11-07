import { Suspense } from 'react';

import { SingleCommentItemType } from '@/api/comment/commentType';
import Loading from '@/components/@common/Loading/Loading';
import ToastUiViewer from '@/components/@common/ToastUiViewer/ToastUiViewer';
import * as S from '@/components/comment/Comment/Comment.styles';
import useDeleteComment from '@/hooks/comment/useDeleteComment';
import useDetailCommentState from '@/hooks/comment/useDetailCommentState';
import { convertGithubAvatarUrlForResize } from '@/utils/converter';
import { dateTimeConverter } from '@/utils/converter';

export interface CommentProps extends Omit<SingleCommentItemType, 'updatedAt'> {
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
	const { handleClickCommentEditButton } = useDetailCommentState({
		articleId,
		commentId: String(id),
		content,
	});
	const { handleClickCommentDeleteButton } = useDeleteComment();

	return (
		<Suspense fallback={<Loading />}>
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
							<S.Button onClick={handleClickCommentEditButton} tabIndex={tabIndex}>
								수정
							</S.Button>
							<S.Button
								onClick={() => {
									handleClickCommentDeleteButton(id);
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
			</S.Container>
		</Suspense>
	);
};

export default Comment;
