import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useSetRecoilState } from 'recoil';

import { getDetailArticle } from '@/api/article/article';
import { DetailArticleResponseType } from '@/api/article/articleType';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { articleState } from '@/store/articleState';

const useGetDetailArticle = (id: string | undefined) => {
	if (typeof id === 'undefined') {
		throw new Error('id를 찾을 수 없습니다.');
	}
	const { data, isSuccess, isError, error } = useQuery<
		DetailArticleResponseType,
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

	return { isSuccess, data };
};

export default useGetDetailArticle;
