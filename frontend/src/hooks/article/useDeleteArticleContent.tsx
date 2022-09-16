import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import { deleteArticle } from '@/api/article';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { URL } from '@/constants/url';
import useSnackBar from '@/hooks/common/useSnackBar';
import { errorPortalState } from '@/store/errorPortalState';

const useDeleteArticleContent = () => {
	const { showSnackBar } = useSnackBar();
	const { isSuccess, isError, error, mutate } = useMutation<
		unknown,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		string
	>(deleteArticle, { retry: 1 });
	const navigate = useNavigate();
	const [errorPortal, setErrorPortal] = useRecoilState(errorPortalState);

	useEffect(() => {
		if (isSuccess) {
			showSnackBar('게시글이 삭제 되었습니다');
			navigate(URL.HOME);
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			if (!error.response?.data?.errorCode) {
				setErrorPortal({ isOpen: true });
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response?.data.errorCode],
			);
		}
	}, [isError]);

	const handleDeleteArticle = (articleId: string) => {
		if (window.confirm('게시글을 삭제하시겠습니까?')) {
			mutate(articleId);
		}
	};

	return { handleDeleteArticle };
};

export default useDeleteArticleContent;
