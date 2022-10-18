import { Dispatch, SetStateAction, useEffect, useRef, useState } from 'react';
import reactDom from 'react-dom';

import AnonymousCheckBox from '@/components/@common/AnonymousCheckBox/AnonymousCheckBox';
import ToastUiEditor from '@/components/@common/ToastUiEditor/ToastUiEditor';
import * as S from '@/components/comment/CommentInputModal/CommentInputModal.styles';
import usePostCommentInputModal from '@/hooks/comment/usePostCommentInputModal';
import usePutCommentInputModal from '@/hooks/comment/usePutCommentInputModal';
import useSnackBar from '@/hooks/common/useSnackBar';
import { queryClient } from '@/index';
import { takeToastImgEditor } from '@/utils/takeToastImgEditor';
import { validatedCommentInput } from '@/utils/validateInput';
import { Editor } from '@toast-ui/react-editor';

export interface CommentInputModalProps {
	closeModal: () => void;
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
	closeModal,
	articleId,
	modalType,
	commentId,
	placeholder = '',
	setTempSavedComment,
}: CommentInputModalProps) => {
	const commentModal = document.getElementById('comment-portal');
	const [isAnonymous, setIsAnonymous] = useState(false);
	const commentContent = useRef<Editor | null>(null);
	const { showSnackBar } = useSnackBar();
	const {
		isLoading: postIsLoading,
		mutate: postMutate,
		isSuccess: postIsSuccess,
	} = usePostCommentInputModal(closeModal);
	const {
		isLoading: putIsLoading,
		mutate: putMutate,
		isSuccess: putIsSuccess,
	} = usePutCommentInputModal(closeModal);

	useEffect(() => {
		if (postIsSuccess || putIsSuccess) {
			queryClient.refetchQueries('comments');
		}
	});

	useEffect(() => {
		takeToastImgEditor(commentContent);
	}, [commentContent]);

	useEffect(() => {
		const commentTempSavedInterval = setInterval(() => {
			if (commentContent.current !== null) {
				setTempSavedComment(commentContent.current.getInstance().getMarkdown());
			}
		}, 1000);
		return () => clearInterval(commentTempSavedInterval);
	}, []);

	if (commentModal === null) {
		throw new Error('모달을 찾지 못하였습니다.');
	}

	const onClickCommentPostButton = () => {
		if (modalType === 'register') {
			handleCreateComment();
			return;
		}
		handleEditComment();
	};

	const handleCreateComment = () => {
		if (commentContent.current == null) {
			return;
		}
		if (!validatedCommentInput(commentContent.current.getInstance().getMarkdown())) {
			showSnackBar('댓글은 1자 이상, 10000자 이하로 작성해주세요');
			return;
		}
		postMutate({
			content: commentContent.current.getInstance().getMarkdown(),
			id: articleId,
			isAnonymous,
		});
	};

	const handleEditComment = () => {
		if (commentContent.current == null) {
			return;
		}
		if (!validatedCommentInput(commentContent.current.getInstance().getMarkdown())) {
			showSnackBar('댓글은 1자 이상, 10000자 이하로 작성해주세요');
			return;
		}
		if (typeof commentId === 'undefined') {
			throw new Error('댓글을 찾지 못하였습니다');
		}
		putMutate({ content: commentContent.current.getInstance().getMarkdown(), commentId });
	};

	if (putIsLoading || postIsLoading) return <div>로딩중...</div>;

	return reactDom.createPortal(
		<S.CommentContainer>
			<S.CommentTitle>{modalStatus[modalType].title}</S.CommentTitle>
			<S.CommentContentBox>
				<ToastUiEditor initContent={placeholder} ref={commentContent} />
			</S.CommentContentBox>
			<S.SubmitBox>
				{modalType === 'register' && <AnonymousCheckBox setIsAnonymous={setIsAnonymous} />}
				<S.CommentPostButton onClick={onClickCommentPostButton}>
					{modalStatus[modalType].buttonText}
				</S.CommentPostButton>
			</S.SubmitBox>
		</S.CommentContainer>,
		commentModal,
	);
};
export default CommentInputModal;
