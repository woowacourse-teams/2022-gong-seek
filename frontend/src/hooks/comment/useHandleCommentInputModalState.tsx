import { useRef, useState, useEffect } from 'react';

import { CommentInputModalProps } from '@/components/comment/CommentInputModal/CommentInputModal';
import usePostCommentInputModal from '@/hooks/comment/usePostCommentInputModal';
import usePutCommentInputModal from '@/hooks/comment/usePutCommentInputModal';
import useModal from '@/hooks/common/useModal';
import useSnackBar from '@/hooks/common/useSnackBar';
import useToastImageConverter from '@/hooks/common/useToastImageConverter';
import { queryClient } from '@/index';
import { validatedCommentInput } from '@/utils/validateInput';
import { Editor } from '@toast-ui/react-editor';

const useHandleCommentInputModalState = ({
	articleId,
	modalType,
	commentId,
	setTempSavedComment,
}: Omit<CommentInputModalProps, 'placeholder'>) => {
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

	useToastImageConverter(commentContent);

	useEffect(() => {
		if (postIsSuccess || putIsSuccess) {
			queryClient.refetchQueries('comments');
		}
	});

	useEffect(() => {
		const commentTempSavedInterval = setInterval(() => {
			if (commentContent.current !== null) {
				setTempSavedComment(commentContent.current.getInstance().getMarkdown());
			}
		}, 1000);
		return () => clearInterval(commentTempSavedInterval);
	}, []);

	const handleClickCommentPostButton = () => {
		if (modalType === 'register') {
			handleCreateComment();
			setTempSavedComment('');
			return;
		}
		handleEditComment();
		setTempSavedComment('');
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

	return {
		commentContent,
		setIsAnonymous,
		handleClickCommentPostButton,
		putIsLoading,
		postIsLoading,
	};
};

export default useHandleCommentInputModalState;
