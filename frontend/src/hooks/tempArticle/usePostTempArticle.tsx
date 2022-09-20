import { AxiosError, AxiosResponse } from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';

import { postTempArticle } from '@/api/tempArticle';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';

export interface postTempArticleProps {
	title: string;
	content: string;
	category: string;
	tags: string[];
	isAnonymous: boolean;
	tempArticleId: number | '';
}

const usePostTempArticle = () => {
	const [tempArticleId, setTempArticleId] = useState<number | ''>('');
	const { isSuccess, isError, isLoading, mutate } = useMutation<
		AxiosResponse<{ id: number }>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{
			title: string;
			content: string;
			tags: string[];
			category: string;
			isAnonymous: boolean;
			tempArticleId: number | '';
		}
	>(['temp-article', tempArticleId], postTempArticle);

	const saveTempArticleId = ({
		title,
		content,
		category,
		tags,
		isAnonymous,
	}: {
		title: string;
		content: string;
		category: string;
		tags: string[];
		isAnonymous: boolean;
	}) => {
		mutate(
			{ title, content, category, tags, isAnonymous, tempArticleId },
			{
				onSuccess: (data) => {
					setTempArticleId(data.data.id);
				},
				onError: (error) => {
					if (!error.response) {
						return;
					}
					throw new CustomError(
						error.response?.data.errorCode,
						ErrorMessage[error.response?.data.errorCode],
					);
				},
			},
		);
	};

	return { isSuccess, isError, isLoading, saveTempArticleId };
};
export default usePostTempArticle;
