import styled from '@emotion/styled';

export const AnonymousCheckInput = styled.input`
	width: ${({ theme }) => theme.size.SIZE_018};
	height: ${({ theme }) => theme.size.SIZE_018};
`;

export const AnonymousBox = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_010};
	justify-content: center;
	align-items: center;
	font-size: ${({ theme }) => theme.size.SIZE_018};

	padding-right: 5%;

	margin-left: auto;
	margin-top: ${({ theme }) => theme.size.SIZE_024};
`;
