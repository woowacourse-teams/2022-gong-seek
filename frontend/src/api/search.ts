import axios from 'axios';

import { HOME_URL } from '@/constants/url';
import { CommonArticleType } from '@/types/articleResponse';
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
		`${HOME_URL}/api/articles/search?searchText=${target}&cursorId=${cursorId}&pageSize=6`,
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
	const { data } = await axios.get<SearchResultType>(
		`${HOME_URL}/api/articles/tags?tagsText=${hashTags}&cursorId=${cursorId}&pageSize=6`,
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
