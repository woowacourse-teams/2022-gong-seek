import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useRecoilState, useSetRecoilState } from 'recoil';

import { getDetailArticle } from '@/api/article';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { articleState } from '@/store/articleState';
import { errorPortalState } from '@/store/errorPortalState';
import { ArticleType } from '@/types/articleResponse';

const useGetDetailArticle = (id: string) => {
	const { data, isError, isSuccess, isLoading, error, isIdle } = useQuery<
		ArticleType,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>(['detail-article', `article${id}`], () => getDetailArticle(id), {
		retry: false,
		refetchOnWindowFocus: false,
	});
	const setTempArticle = useSetRecoilState(articleState);

	const [errorPortal, setErrorPortal] = useRecoilState(errorPortalState);

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

	useEffect(() => {
		if (isSuccess) {
			setTempArticle({ title: data.title, content: data.content, tag: data.tag });
		}
	}, [isSuccess]);

	return { isSuccess, isLoading, data, isIdle };
};

export default useGetDetailArticle;
