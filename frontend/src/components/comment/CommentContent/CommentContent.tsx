import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import Comment from '@/components/comment/Comment/Comment';
import * as S from '@/components/comment/CommentContent/CommentContent.styles';
import { URL } from '@/constants/url';
import useModal from '@/hooks/common/useModal';
import { getUserIsLogin } from '@/store/userState';
import { CommentType } from '@/types/commentResponse';

export interface CommentContentProps {
	articleId: string;
	commentList: CommentType[];
}

const CommentContent = ({ articleId, commentList }: CommentContentProps) => {
	const navigate = useNavigate();
	const { showModal } = useModal();
	const isLogin = useRecoilValue(getUserIsLogin);

	const handleClickCommentButton = () => {
		if (isLogin) {
			showModal({
				modalType: 'comment-modal',
				modalProps: {
					articleId,
					modalType: 'register',
					placeholder: '',
				},
				isMobileOnly: false,
			});
			return;
		}

		if (window.confirm('로그인이 필요한 서비스입니다. 로그인 창으로 이동하시겠습니까?')) {
			navigate(URL.LOGIN);
		}
	};

	return (
		<>
			<S.CommentSection>
				<S.CommentInputBox>
					<S.CommentInput
						aria-label="댓글을 입력하는 창으로 이동하는 링크 입니다"
						role="link"
						onClick={handleClickCommentButton}
						disabled={!isLogin}
						tabIndex={0}
					/>
					<S.CreateCommentButton
						aria-label="댓글을 입력하는 창으로 이동하는 링크입니다."
						role="link"
						onClick={handleClickCommentButton}
						disabled={!isLogin}
						tabIndex={0}
					/>
				</S.CommentInputBox>

				<S.CommentHeader>
					<S.CommentTitle>댓글</S.CommentTitle>
					<S.CommentTotal>
						<S.CommentIcon />
						<div aria-label={`댓글 갯수 ${commentList.length}`} tabIndex={0}>
							{commentList.length || 0}개
						</div>
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
							tabIndex={0}
						/>
					))
				) : (
					<EmptyMessage>첫 번째 댓글을 달아주세요!</EmptyMessage>
				)}
			</S.CommentSection>
		</>
	);
};

export default CommentContent;
