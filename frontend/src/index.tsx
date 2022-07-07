import { QueryClient, QueryClientProvider } from 'react-query';
import ReactDOM from 'react-dom/client';
import React from 'react';
import App from '@/App';
import { ThemeProvider, Global } from '@emotion/react';
import { RecoilRoot } from 'recoil';
import { BrowserRouter } from 'react-router-dom';
import { reset } from '@/styles/reset';
import { theme } from '@/styles/Theme';
import { worker } from './mock/browser';

if (process.env.NODE_ENV === 'development') {
	worker.start();
}

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

const queryClient = new QueryClient();

root.render(
	<ThemeProvider theme={theme}>
		<Global styles={reset} />
		<QueryClientProvider client={queryClient}>
			<RecoilRoot>
				<BrowserRouter>
					<App />
				</BrowserRouter>
			</RecoilRoot>
		</QueryClientProvider>
	</ThemeProvider>,
);
