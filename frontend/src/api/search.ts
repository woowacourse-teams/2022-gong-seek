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
	const { data } = await axios.get<SearchResultType>(
		`${HOME_URL}/api/articles/search?searchText=${target}&cursorId=${cursorId}&pageSize=5`,
	);

	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId: '',
	};
};
