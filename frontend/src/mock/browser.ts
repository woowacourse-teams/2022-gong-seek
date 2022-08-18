import { setupWorker } from 'msw';

import {
	LoginHandler,
	ArticleHandler,
	CommentHandler,
	MyPageHandler,
	SearchHandler,
	HashTagHandler,
	LikeHandler,
	VoteHandler,
} from '@/mock/index';

export const worker = setupWorker(
	...LoginHandler,
	...ArticleHandler,
	...CommentHandler,
	...MyPageHandler,
	...SearchHandler,
	...HashTagHandler,
	...LikeHandler,
	...VoteHandler,
);
