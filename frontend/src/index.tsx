import ReactDOM from 'react-dom/client';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import App from '@/App';
import LogicErrorBoundary from '@/components/helper/LogicErrorBoundary';
import UIErrorBoundary from '@/components/helper/UIErrorBoundary';
import { worker } from '@/mock/browser';
import NotFound from '@/pages/NotFound';
import { theme } from '@/styles/Theme';
import { reset } from '@/styles/reset';
import { ThemeProvider, Global } from '@emotion/react';

// if (process.env.NODE_ENV === 'development') {
// 	worker.start();
// }

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

export const queryClient = new QueryClient();

root.render(
	<ThemeProvider theme={theme}>
		<Global styles={reset} />
		<QueryClientProvider client={queryClient}>
			<RecoilRoot>
				<BrowserRouter>
					<UIErrorBoundary
						serverErrorFallback={<div>서버 에러입니다!</div>}
						NotFoundErrorFallback={<NotFound />}
					>
						<LogicErrorBoundary>
							<App />
						</LogicErrorBoundary>
					</UIErrorBoundary>
				</BrowserRouter>
			</RecoilRoot>
		</QueryClientProvider>
	</ThemeProvider>,
);
