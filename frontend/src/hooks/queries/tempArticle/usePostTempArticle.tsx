import { AxiosError, AxiosResponse } from 'axios';
import { Dispatch, SetStateAction } from 'react';
import { useMutation } from 'react-query';

import { postTempArticle } from '@/api/tempArticle';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useSnackBar from '@/hooks/common/useSnackBar';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

export interface postTempArticleProps {
	title: string;
	content: string;
	category: string;
	tag: string[];
	isAnonymous: boolean;
	tempArticleId: number | '';
}

const usePostTempArticle = ({
	tempArticleId,
	setTempArticleId,
}: {
	tempArticleId: number | '';
	setTempArticleId: Dispatch<SetStateAction<number | ''>>;
}) => {
	const { isSuccess, isError, isLoading, error, mutate } = useMutation<
		AxiosResponse<{ id: number }>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{
			title: string;
			content: string;
			tag: string[];
			category: string;
			isAnonymous: boolean;
			tempArticleId: number | '';
		}
	>(['temp-article', tempArticleId], postTempArticle);
	const { showSnackBar } = useSnackBar();

	useThrowCustomError(isError, error);

	const saveTempArticleId = ({
		title,
		content,
		category,
		tag,
		isAnonymous,
	}: {
		title: string;
		content: string;
		category: string;
		tag: string[];
		isAnonymous: boolean;
	}) => {
		mutate(
			{ title, content, category, tag, isAnonymous, tempArticleId },
			{
				onSuccess: (data) => {
					setTempArticleId(data.data.id);
					showSnackBar('작성 중이신 글이 임시저장 되었습니다');
				},
			},
		);
	};

	return { isSuccess, isError, isLoading, saveTempArticleId };
};
export default usePostTempArticle;
