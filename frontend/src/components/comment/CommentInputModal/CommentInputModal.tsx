import { useEffect, useRef, useState } from 'react';

import { postImageUrlConverter } from '@/api/image';
import AnonymousCheckBox from '@/components/@common/AnonymousCheckBox/AnonymousCheckBox';
import ToastUiEditor from '@/components/@common/ToastUiEditor/ToastUiEditor';
import * as S from '@/components/comment/CommentInputModal/CommentInputModal.styles';
import usePostCommentInputModal from '@/hooks/comment/usePostCommentInputModal';
import usePutCommentInputModal from '@/hooks/comment/usePutCommentInputModal';
import useModal from '@/hooks/common/useModal';
import useSnackBar from '@/hooks/common/useSnackBar';
import { queryClient } from '@/index';
import { validatedCommentInput } from '@/utils/validateInput';
import { Editor } from '@toast-ui/react-editor';

export interface CommentInputModalProps {
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
	articleId,
	modalType,
	commentId,
	placeholder = '',
}: CommentInputModalProps) => {
	const [isAnonymous, setIsAnonymous] = useState(false);
	const commentContent = useRef<Editor | null>(null);
	const { showSnackBar } = useSnackBar();
	const { hideModal } = useModal();

	const {
		isLoading: postIsLoading,
		mutate: postMutate,
		isSuccess: postIsSuccess,
	} = usePostCommentInputModal(hideModal);
	const {
		isLoading: putIsLoading,
		mutate: putMutate,
		isSuccess: putIsSuccess,
	} = usePutCommentInputModal(hideModal);

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

	return (
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
		</S.CommentContainer>
	);
};
export default CommentInputModal;
