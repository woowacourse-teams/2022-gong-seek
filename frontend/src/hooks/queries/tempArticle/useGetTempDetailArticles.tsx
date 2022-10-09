import { AxiosError, AxiosResponse } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getTempDetailArticle } from '@/api/tempArticle';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { TempArticleDetailResponse } from '@/types/articleResponse';
import { Editor } from '@toast-ui/react-editor';

interface useGetTempArticlesProps {
	tempArticleId: number | '';
	setWritingArticlesInfo: (tempArticleData: AxiosResponse<TempArticleDetailResponse>) => void;
	setEditorContent: (
		content: Editor | null,
		tempArticleData: AxiosResponse<TempArticleDetailResponse>,
	) => void;
	content: Editor | null;
}

const useGetTempDetailArticles = ({
	tempArticleId,
	setEditorContent,
	setWritingArticlesInfo,
	content,
}: useGetTempArticlesProps) => {
	const { data, isSuccess, isError, error } = useQuery<
		AxiosResponse<TempArticleDetailResponse>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>(['temp-detail-article', tempArticleId], () => getTempDetailArticle({ id: tempArticleId }), {
		enabled: typeof tempArticleId === 'number',
	});

	useEffect(() => {
		if (isSuccess) {
			if (data && data.data) {
				setWritingArticlesInfo(data);
			}
			setEditorContent(content, data);
		}
	}, [isSuccess]);

	useThrowCustomError(isError, error);
};

export default useGetTempDetailArticles;
