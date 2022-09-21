import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useSetRecoilState } from 'recoil';

import { getDetailArticle } from '@/api/article';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { articleState } from '@/store/articleState';
import { ArticleType } from '@/types/articleResponse';

const useGetDetailArticle = (id: string) => {
	const { data, isError, isSuccess, isLoading, error, isIdle } = useQuery<
		ArticleType,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>(['detail-article', `article${id}`], () => getDetailArticle(id), {
		retry: false,
	});
	const setTempArticle = useSetRecoilState(articleState);

	useEffect(() => {
		if (isError) {
			if (!error.response) {
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response.data.errorCode],
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
