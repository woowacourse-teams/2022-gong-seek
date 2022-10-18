import { useRef, useState, useEffect } from 'react';

import { CommentInputModalProps } from '@/components/comment/CommentInputModal/CommentInputModal';
import usePostCommentInputModal from '@/hooks/comment/usePostCommentInputModal';
import usePutCommentInputModal from '@/hooks/comment/usePutCommentInputModal';
import useSnackBar from '@/hooks/common/useSnackBar';
import { queryClient } from '@/index';
import { takeToastImgEditor } from '@/utils/takeToastImgEditor';
import { validatedCommentInput } from '@/utils/validateInput';
import { Editor } from '@toast-ui/react-editor';

const useHandleCommentInputModalState = ({
	closeModal,
	articleId,
	modalType,
	commentId,
	setTempSavedComment,
}: Omit<CommentInputModalProps, 'placeholder'>) => {
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

	return {
		commentContent,
		setIsAnonymous,
		onClickCommentPostButton,
		putIsLoading,
		postIsLoading,
	};
};

export default useHandleCommentInputModalState;
