import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useSetRecoilState } from 'recoil';

import { getDetailArticle } from '@/api/article';
import CustomError from '@/components/helper/CustomError';
import { articleState } from '@/store/articleState';
import { ArticleType } from '@/types/articleResponse';

const useGetDetailArticle = (id: string) => {
	const { data, isError, isSuccess, isLoading, error, isIdle } = useQuery<
		ArticleType,
		AxiosError<{ errorCode: string; message: string }>
	>(['detail-article', `article${id}`], () => getDetailArticle(id));
	const setTempArticle = useSetRecoilState(articleState);

	useEffect(() => {
		if (isError) {
			throw new CustomError(error.response?.data.errorCode, error.response?.data.message);
		}
	}, [isError]);

	useEffect(() => {
		if (isSuccess) {
			setTempArticle({ title: data.title, content: data.content });
		}
	}, [isSuccess]);

	return { isSuccess, isLoading, data, isIdle };
};

export default useGetDetailArticle;
