import { AxiosError, AxiosResponse } from 'axios';
import { useEffect, useRef, useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { postWritingArticle } from '@/api/article';
import CustomError from '@/components/helper/CustomError';
import { CATEGORY } from '@/constants/categoryType';
import { Editor } from '@toast-ui/react-editor';

const usePostWritingArticles = ({
	category,
	isAnonymous,
}: {
	category?: string;
	isAnonymous: boolean;
}) => {
	const { data, mutate, isError, isLoading, isSuccess, error } = useMutation<
		AxiosResponse<{ id: string }>,
		AxiosError<{ errorCode: string; message: string }>,
		{ title: string; category: string; content: string; hashTag: string[]; isAnonymous: boolean }
	>(postWritingArticle);
	const content = useRef<Editor | null>(null);
	const [title, setTitle] = useState<string>('');
	const [categoryOption, setCategoryOption] = useState<string>(category ? category : '');
	const [hashTags, setHashTags] = useState<string[]>([]);

	const navigate = useNavigate();

	if (typeof category === 'undefined') {
		throw new Error('카테고리가 존재하지 않습니다.');
	}

	useEffect(() => {
		if (isSuccess && category === CATEGORY.discussion) {
			if (confirm('글 등록이 완료되었습니다. 투표를 등록하시겠습니까?')) {
				navigate(`/votes/${data.data.id}`);
				return;
			}
			navigate(`/articles/${category}/${data.data.id}`);
		}
		if (isSuccess && category === CATEGORY.question) {
			navigate(`/articles/${category}/${data.data.id}`);
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			throw new CustomError(error.response?.data.errorCode, error.response?.data.message);
		}
	}, []);

	const handleSubmitButtonClick = (categoryOption: string) => {
		if (content.current === null) {
			return;
		}
		mutate({
			title: title,
			category: categoryOption,
			content: content.current.getInstance().getMarkdown(),
			hashTag: hashTags,
			isAnonymous,
		});
	};

	return {
		handleSubmitButtonClick,
		content,
		isLoading,
		title,
		setTitle,
		hashTags,
		setHashTags,
		categoryOption,
		setCategoryOption,
	};
};

export default usePostWritingArticles;
