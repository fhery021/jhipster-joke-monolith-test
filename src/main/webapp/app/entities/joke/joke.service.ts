import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJoke } from 'app/shared/model/joke.model';

type EntityResponseType = HttpResponse<IJoke>;
type EntityArrayResponseType = HttpResponse<IJoke[]>;

@Injectable({ providedIn: 'root' })
export class JokeService {
  public resourceUrl = SERVER_API_URL + 'api/jokes';

  constructor(protected http: HttpClient) {}

  create(joke: IJoke): Observable<EntityResponseType> {
    return this.http.post<IJoke>(this.resourceUrl, joke, { observe: 'response' });
  }

  update(joke: IJoke): Observable<EntityResponseType> {
    return this.http.put<IJoke>(this.resourceUrl, joke, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJoke>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJoke[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  // like(joke: IJoke, userId: string): Observable<HttpResponse<{}>>{
  //   // joke.reactions?.push({

  //   // })
  //   // return this.http.put<IJoke>(this.resourceUrl,{})
  //   // joke.reactions.
  //   return null;
  // }
}
