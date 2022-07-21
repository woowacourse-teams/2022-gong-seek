import reactDom from 'react-dom';
import * as S from '@/components/common/CommentInputModal/CommentInputModal.styles';
import { useEffect, useState } from 'react';
import { useMutation } from 'react-query';
import { postComments, putComments } from '@/api/comments';

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
	placeholder,
}: CommentInputModalProps) => {
	const commentModal = document.getElementById('comment-portal');
	const [comment, setComment] = useState('');
	const {
		isLoading: postIsLoading,
		isError: postIsError,
		isSuccess: postIsSuccess,
		mutate: postMutate,
	} = useMutation(postComments);

	const {
		isLoading: putIsLoading,
		isError: putIsError,
		isSuccess: putIsSuccess,
		mutate: putMutate,
	} = useMutation(putComments);

	if (commentModal === null) {
		throw new Error('모달을 찾지 못하였습니다.');
	}

	const onClickCommentPostButton = () => {
		if (modalType === 'register') {
			postMutate({ content: comment, id: articleId });
			return;
		}
		if (typeof commentId === 'undefined') {
			throw new Error('댓글을 찾지 못하였습니다.');
		}

		putMutate({ content: comment, commentId });
	};

	useEffect(() => {
		if (postIsSuccess) {
			alert('댓글이 등록되었습니다.');
			closeModal();
			return;
		}

		if (putIsSuccess) {
			alert('댓글이 수정되었습니다.');
			closeModal();
		}
	});

	if (putIsLoading || postIsLoading) return <div>로딩중...</div>;

	if (postIsError || putIsError) return <div>에러...!</div>;

	return reactDom.createPortal(
		<S.CommentContainer>
			<S.CommentTitle>{modalStatus[modalType].title}</S.CommentTitle>
			<S.CommentContent
				aria-label="댓글을 입력해주세요"
				value={comment}
				onChange={(e) => setComment(e.target.value)}
				placeholder={placeholder}
			></S.CommentContent>
			<S.CommentPostButton onClick={onClickCommentPostButton}>
				{modalStatus[modalType].buttonText}
			</S.CommentPostButton>
		</S.CommentContainer>,
		commentModal,
	);
};
export default CommentInputModal;
