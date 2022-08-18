import { setupWorker } from 'msw';

import {
	LoginHandler,
	ArticleHandler,
	CommentHandler,
	MyPageHandler,
	SearchHandler,
	HashTagHandler,
} from '@/mock/index';

// import { VoteHandler } from '@/mock/vote';

export const worker = setupWorker(
	...LoginHandler,
	...ArticleHandler,
	...CommentHandler,
	...MyPageHandler,
	...SearchHandler,
	...HashTagHandler,
);
