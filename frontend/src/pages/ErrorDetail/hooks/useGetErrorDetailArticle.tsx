import { getDetailArticle } from '@/api/article';
import { articleState } from '@/store/articleState';
import { ArticleType } from '@/types/articleResponse';
import { useEffect } from 'react';
import { AxiosError } from 'axios';
import { useQuery } from 'react-query';
import { useSetRecoilState } from 'recoil';

const useGetErrorDetailArticle = (id: string) => {
	const { data, isError, isSuccess, isLoading, error, isIdle } = useQuery<ArticleType, AxiosError>(
		['detail-article', `article${id}`],
		() => getDetailArticle(id),
	);
	const setTempArticle = useSetRecoilState(articleState);

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
		}
	}, [isError]);

	useEffect(() => {
		if (isSuccess) {
			setTempArticle({ title: data.title, content: data.content });
		}
	}, [isSuccess]);

	return { isSuccess, isLoading, data, isIdle };
};

export default useGetErrorDetailArticle;
