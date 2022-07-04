import { setupWorker } from 'msw';

import { LoginHandler } from '@/mock/index';

export const worker = setupWorker(...LoginHandler);
