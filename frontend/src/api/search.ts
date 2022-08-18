import axios from 'axios';

import { HOME_URL } from '@/constants/url';
import { SearchResultType } from '@/types/searchResponse';

export const getSearchResult = async ({
	target,
	cursorId = '',
}: {
	target: string;
	cursorId: string;
}) => {
	const accessToken = localStorage.getItem('accessToken');
	const encodedTarget = encodeURIComponent(target);
	const { data } = await axios.get<SearchResultType>(
		`${HOME_URL}/api/articles/search?searchText=${encodedTarget}&cursorId=${cursorId}&pageSize=6`,
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);

	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId: String(data.articles[data.articles.length - 1].id),
		target: target,
	};
};

export const getArticleByHashTag = async ({
	hashTags,
	cursorId = '',
}: {
	hashTags: string;
	cursorId: string;
}) => {
	const accessToken = localStorage.getItem('accessToken');
	const encodedTarget = encodeURIComponent(hashTags);
	const { data } = await axios.get<SearchResultType>(
		`${HOME_URL}/api/articles/search/tags?tagsText=${encodedTarget}&cursorId=${cursorId}&pageSize=6`,
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId: String(data.articles[data.articles.length - 1].id),
		hashTags: hashTags,
	};
};
