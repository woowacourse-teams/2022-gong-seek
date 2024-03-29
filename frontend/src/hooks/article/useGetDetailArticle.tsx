import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useSetRecoilState } from 'recoil';

import { getDetailArticle } from '@/api/article';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { articleState } from '@/store/articleState';
import { ArticleType } from '@/types/articleResponse';

const useGetDetailArticle = (id: string) => {
	const { data, isSuccess, isError, isLoading, error, isIdle } = useQuery<
		ArticleType,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>(['detail-article', `article${id}`], () => getDetailArticle(id), {
		retry: false,
	});
	const setTempArticle = useSetRecoilState(articleState);

	useThrowCustomError(isError, error);

	useEffect(() => {
		if (isSuccess) {
			setTempArticle({ title: data.title, content: data.content, tag: data.tag });
		}
	}, [isSuccess]);

	return { isSuccess, isLoading, data, isIdle };
};

export default useGetDetailArticle;
