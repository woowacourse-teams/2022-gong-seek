import { useEffect, useRef, useState } from 'react';
import reactDom from 'react-dom';

import { postImageUrlConverter } from '@/api/image';
import AnonymouseCheckBox from '@/components/common/AnonymousCheckBox/AnonymouseCheckBox';
import * as S from '@/components/common/CommentInputModal/CommentInputModal.styles';
import ToastUiEditor from '@/components/common/ToastUiEditor/ToastUiEditor';
import usePostCommentInputModal from '@/hooks/comment/usePostCommentInputModal';
import usePutCommentInputModal from '@/hooks/comment/usePutCommentInputModal';
import useSnackBar from '@/hooks/common/useSnackBar';
import { queryClient } from '@/index';
import { validatedCommentInput } from '@/utils/validateInput';
import { Editor } from '@toast-ui/react-editor';

export interface CommentInputModalProps {
	closeModal: () => void;
	articleId: string;
	modalType: 'edit' | 'register';
	commentId?: string;
	placeholder: string;
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
}: CommentInputModalProps) => {
	//TODO: custom hook으로 고쳐야함
	//TODO: 현재 여기서 수정, 등록의 모든 것을 관리하고 있다.
	//TODO: UI는 재활용하고 로직은 각각 분리하는것이 좋을것 같다! 공통되는 로직이 있다면 훅으로 처리
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
		if (commentContent.current) {
			commentContent.current.getInstance().removeHook('addImageBlobHook');
			commentContent.current.getInstance().addHook('addImageBlobHook', (blob, callback) => {
				(async () => {
					const formData = new FormData();
					formData.append('imageFile', blob);
					const url = await postImageUrlConverter(formData);
					callback(url, 'alt-text');
				})();
			});
		}
	}, [commentContent]);

	if (commentModal === null) {
		throw new Error('모달을 찾지 못하였습니다.');
	}

	const onClickCommentPostButton = () => {
		if (commentContent.current == null) {
			return;
		}
		if (!validatedCommentInput(commentContent.current.getInstance().getMarkdown())) {
			showSnackBar('댓글은 1자 이상, 10000자 이하로 작성해주세요');
			return;
		}
		if (commentContent.current)
			if (modalType === 'register') {
				postMutate({
					content: commentContent.current.getInstance().getMarkdown(),
					id: articleId,
					isAnonymous,
				});
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
				{modalType === 'register' && <AnonymouseCheckBox setIsAnonymous={setIsAnonymous} />}
				<S.CommentPostButton onClick={onClickCommentPostButton}>
					{modalStatus[modalType].buttonText}
				</S.CommentPostButton>
			</S.SubmitBox>
		</S.CommentContainer>,
		commentModal,
	);
};
export default CommentInputModal;
