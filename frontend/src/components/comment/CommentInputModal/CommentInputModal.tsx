import { Dispatch, SetStateAction } from 'react';

import AnonymousCheckBox from '@/components/@common/AnonymousCheckBox/AnonymousCheckBox';
import ToastUiEditor from '@/components/@common/ToastUiEditor/ToastUiEditor';
import * as S from '@/components/comment/CommentInputModal/CommentInputModal.styles';
import useHandleCommentInputModalState from '@/hooks/comment/useHandleCommentInputModalState';

export interface CommentInputModalProps {
	articleId: string;
	modalType: 'edit' | 'register';
	commentId?: string;
	placeholder: string;
	setTempSavedComment: Dispatch<SetStateAction<string>>;
}

const modalStatus = {
	edit: {
		title: '댓글 수정하기',
		buttonText: '수정하기',
	},
	register: {
		title: '댓글 등록하기',
		buttonText: '등록하기',
	},
} as const;

const CommentInputModal = ({
	articleId,
	modalType,
	commentId,
	placeholder = '',
	setTempSavedComment,
}: CommentInputModalProps) => {
	const {
		commentContent,
		setIsAnonymous,
		handleClickCommentPostButton,
		putIsLoading,
		postIsLoading,
	} = useHandleCommentInputModalState({
		articleId,
		modalType,
		commentId,
		setTempSavedComment,
	});

	if (putIsLoading || postIsLoading) return <div>로딩중...</div>;

	return (
		<S.CommentContainer>
			<S.CommentTitle>{modalStatus[modalType].title}</S.CommentTitle>
			<S.CommentContentBox>
				<ToastUiEditor initContent={placeholder} ref={commentContent} />
			</S.CommentContentBox>
			<S.SubmitBox>
				{modalType === 'register' && <AnonymousCheckBox setIsAnonymous={setIsAnonymous} />}
				<S.CommentPostButton onClick={handleClickCommentPostButton}>
					{modalStatus[modalType].buttonText}
				</S.CommentPostButton>
			</S.SubmitBox>
		</S.CommentContainer>
	);
};
export default CommentInputModal;
